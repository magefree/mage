package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ThePrydwenSteelFlagshipHumanKnightToken;
import mage.watchers.common.ArtifactEnteredControllerWatcher;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class ThePrydwenSteelFlagship extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("another nontoken artifact you control");

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

        // Whenever another nontoken artifact you control enters,
        // create a 2/2 white Human Knight creature token with
        // "This creature gets +2/+2 as long as an artifact entered the battlefield under your control this turn."
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new ThePrydwenSteelFlagshipHumanKnightToken()), filter
        ), new ArtifactEnteredControllerWatcher());

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
