package mage.cards.t;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ThePrydwenSteelFlagshipHumanKnightToken;
import mage.watchers.common.ArtifactEnteredControllerWatcher;

/**
 * @author Cguy7777
 */
public final class ThePrydwenSteelFlagship extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("another nontoken artifact");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public ThePrydwenSteelFlagship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another nontoken artifact enters the battlefield under your control,
        // create a 2/2 white Human Knight creature token with
        // "This creature gets +2/+2 as long as an artifact entered the battlefield under your control this turn."
        this.addAbility(
                new EntersBattlefieldControlledTriggeredAbility(
                        new CreateTokenEffect(new ThePrydwenSteelFlagshipHumanKnightToken()), filter),
                new ArtifactEnteredControllerWatcher());

        // Crew 2
        this.addAbility(new CrewAbility(2));

    }

    private ThePrydwenSteelFlagship(final ThePrydwenSteelFlagship card) {
        super(card);
    }

    @Override
    public ThePrydwenSteelFlagship copy() {
        return new ThePrydwenSteelFlagship(this);
    }
}
