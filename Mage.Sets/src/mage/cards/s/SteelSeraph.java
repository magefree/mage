package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SteelSeraph extends CardImpl {

    public SteelSeraph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Prototype {1}{W}{W} -- 3/3
        this.addAbility(new PrototypeAbility(this, "{1}{W}{W}", 3, 3));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, target creature you control gains your choice of flying, vigilance, or lifelink until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new SteelSeraphEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private SteelSeraph(final SteelSeraph card) {
        super(card);
    }

    @Override
    public SteelSeraph copy() {
        return new SteelSeraph(this);
    }
}

class SteelSeraphEffect extends OneShotEffect {

    private static final Map<String, Ability> map = new HashMap<>();

    static {
        map.put("Flying", FlyingAbility.getInstance());
        map.put("Vigilance", VigilanceAbility.getInstance());
        map.put("Lifelink", LifelinkAbility.getInstance());
    }

    SteelSeraphEffect() {
        super(Outcome.Benefit);
        staticText = "target creature you control gains your choice of flying, vigilance, or lifelink until end of turn";
    }

    private SteelSeraphEffect(final SteelSeraphEffect effect) {
        super(effect);
    }

    @Override
    public SteelSeraphEffect copy() {
        return new SteelSeraphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose an ability");
        choice.setChoices(map.keySet());
        player.choose(outcome, choice, game);
        Ability ability = map.getOrDefault(choice.getChoice(), null);
        if (ability == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(ability), source);
        return true;
    }
}
