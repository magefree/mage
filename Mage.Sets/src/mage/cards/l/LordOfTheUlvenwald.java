package mage.cards.l;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LordOfTheUlvenwald extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wolves and Werewolves");

    static {
        filter.add(Predicates.or(
                SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()
        ));
    }

    public LordOfTheUlvenwald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.color.setRed(true);
        this.color.setGreen(true);
        this.nightCard = true;
        this.transformable = true;

        // Other Wolves and Werewolves you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Whenever Lord of the Ulvenwald attacks, add {R} or {G}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new AttacksTriggeredAbility(new LordOfTheUlvenwaldEffect()));

        // Nightbound
        this.addAbility(NightboundAbility.getInstance());
    }

    private LordOfTheUlvenwald(final LordOfTheUlvenwald card) {
        super(card);
    }

    @Override
    public LordOfTheUlvenwald copy() {
        return new LordOfTheUlvenwald(this);
    }
}

class LordOfTheUlvenwaldEffect extends OneShotEffect {

    LordOfTheUlvenwaldEffect() {
        super(Outcome.Benefit);
        staticText = "add {R} or {G}. Until end of turn, you don't lose this mana as steps and phases end";
    }

    private LordOfTheUlvenwaldEffect(final LordOfTheUlvenwaldEffect effect) {
        super(effect);
    }

    @Override
    public LordOfTheUlvenwaldEffect copy() {
        return new LordOfTheUlvenwaldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Mana mana = player.chooseUse(
                Outcome.Neutral, "Choose red or green", null,
                "Red", "Green", source, game
        ) ? Mana.RedMana(1) : Mana.GreenMana(1);
        player.getManaPool().addMana(mana, game, source, true);
        return true;
    }
}
