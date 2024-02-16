package mage.cards.e;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.AngelWarriorToken;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class EmeriasCall extends ModalDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("Non-Angel creatures you control");

    static {
        filter.add(Predicates.not(SubType.ANGEL.getPredicate()));
    }

    public EmeriasCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{4}{W}{W}{W}",
                "Emeria, Shattered Skyclave", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Emeria's Call
        // Sorcery

        // Create two 4/4 white Angel Warrior creature tokens with flying. Non-Angel creatures you control gain indestructible until your next turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new AngelWarriorToken(), 2));
        this.getLeftHalfCard().getSpellAbility().addEffect(new GainAbilityAllEffect(
                IndestructibleAbility.getInstance(),
                Duration.UntilYourNextTurn, filter
        ));

        // 2.
        // Emeria, Shattered Skyclave
        // Land

        // As Emeria, Shattered Skyclave enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {W}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
    }

    private EmeriasCall(final EmeriasCall card) {
        super(card);
    }

    @Override
    public EmeriasCall copy() {
        return new EmeriasCall(this);
    }
}
