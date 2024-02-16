package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CelestineTheLivingSaint extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card in your graveyard with mana value less than or equal to the life you gained this turn"
    );

    static {
        filter.add(CelestineTheLivingSaintPredicate.instance);
    }

    public CelestineTheLivingSaint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Healing Tears -- At the beginning of your end step, return target creature card with mana value X or less from your graveyard to the battlefield, where X is the amount of life you gained this turn.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return target creature card with mana value X or less from your graveyard to the battlefield, " +
                        "where X is the amount of life you gained this turn"), TargetController.YOU, false);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(
                ability.withFlavorWord("Healing Tears")
                       .addHint(ControllerGainedLifeCount.getHint()),
                new PlayerGainedLifeWatcher()
        );
    }

    private CelestineTheLivingSaint(final CelestineTheLivingSaint card) {
        super(card);
    }

    @Override
    public CelestineTheLivingSaint copy() {
        return new CelestineTheLivingSaint(this);
    }
}

enum CelestineTheLivingSaintPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input
                .getObject()
                .getManaValue()
                <= game
                .getState()
                .getWatcher(PlayerGainedLifeWatcher.class)
                .getLifeGained(input.getSource().getControllerId());
    }
}