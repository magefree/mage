package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ShiftingGrift extends CardImpl {

    public ShiftingGrift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}");


        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {2} -- Exchange control of two target creatures.
        this.getSpellAbility().addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, "Exchange control of two target creatures"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(2));

        // + {1} -- Exchange control of two target artifacts.
        this.getSpellAbility().addMode(new Mode(new ExchangeControlTargetEffect(Duration.EndOfGame, "Exchange control of two target artifacts"))
                .addTarget(new TargetArtifactPermanent(2))
                .withCost(new GenericManaCost(1)));

        // + {1} -- Exchange control of two target enchantments.
        this.getSpellAbility().addMode(new Mode(new ExchangeControlTargetEffect(Duration.EndOfGame, "Exchange control of two target enchantments"))
                .addTarget(new TargetEnchantmentPermanent(2))
                .withCost(new GenericManaCost(1)));
    }

    private ShiftingGrift(final ShiftingGrift card) {
        super(card);
    }

    @Override
    public ShiftingGrift copy() {
        return new ShiftingGrift(this);
    }
}
