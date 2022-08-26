package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.BirdToken;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author weirddan455
 */
public final class AetherChanneler extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("another nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AetherChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Aether Channeler enters the battlefield, choose one --
        // * Create a 1/1 white Bird creature token with flying.
        // * Return another target nonland permanent to its owner's hand.
        // * Draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BirdToken()));
        Mode mode = new Mode(new ReturnToHandTargetEffect()
                .setText("Return another target nonland permanent to its owner's hand."));
        mode.addTarget(new TargetNonlandPermanent(filter));
        ability.addMode(mode);
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1)));
        this.addAbility(ability);
    }

    private AetherChanneler(final AetherChanneler card) {
        super(card);
    }

    @Override
    public AetherChanneler copy() {
        return new AetherChanneler(this);
    }
}
