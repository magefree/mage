package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;

/**
 *
 * @author muz
 */
public final class DamoclesBaseSwordOfKang extends CardImpl {

    public DamoclesBaseSwordOfKang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Damocles Base deals combat damage to a player, that player faces a villainous choice -- They sacrifice a nontoken creature of their choice, or they lose 2 life and you draw two cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
            new DamoclesBaseSwordOfKangEffect(), false, true
        ));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private DamoclesBaseSwordOfKang(final DamoclesBaseSwordOfKang card) {
        super(card);
    }

    @Override
    public DamoclesBaseSwordOfKang copy() {
        return new DamoclesBaseSwordOfKang(this);
    }
}

class DamoclesBaseSwordOfKangEffect extends OneShotEffect {

    private static final FaceVillainousChoice choice = new FaceVillainousChoice(
            Outcome.Sacrifice,
            new DamoclesBaseSwordOfKangFirstChoice(),
            new DamoclesBaseSwordOfKangSecondChoice()
    );

    DamoclesBaseSwordOfKangEffect() {
        super(Outcome.Benefit);
        staticText = "that player " + choice.generateRule();
    }

    private DamoclesBaseSwordOfKangEffect(final DamoclesBaseSwordOfKangEffect effect) {
        super(effect);
    }

    @Override
    public DamoclesBaseSwordOfKangEffect copy() {
        return new DamoclesBaseSwordOfKangEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        return choice.faceChoice(player, game, source);
    }
}

class DamoclesBaseSwordOfKangFirstChoice extends VillainousChoice {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    DamoclesBaseSwordOfKangFirstChoice() {
        super("That player sacrifices a nontoken creature of their choice", "Sacrifice a nontoken creature");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        if (!game.getBattlefield().contains(filter, player.getId(), source, game, 1)) {
            return false;
        }
        TargetSacrifice target = new TargetSacrifice(filter);
        player.choose(Outcome.Sacrifice, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.sacrifice(source, game);
    }
}

class DamoclesBaseSwordOfKangSecondChoice extends VillainousChoice {

    DamoclesBaseSwordOfKangSecondChoice() {
        super("that player loses 2 life and you draw two cards", "Lose 2 life and {controller} draws two cards");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        boolean lifeLost = player.loseLife(2, game, source, false) > 0;
        boolean cardsDrawn = controller != null && controller.drawCards(2, source, game) > 0;
        return lifeLost || cardsDrawn;
    }
}
