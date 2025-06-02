package mage.cards.i;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.abilities.keyword.TieredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IceMagic extends CardImpl {

    public IceMagic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Tiered
        this.addAbility(new TieredAbility(this));

        // * Blizzard -- {0} -- Return target creature to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(0));
        this.getSpellAbility().withFirstModeFlavorWord("Blizzard");

        // * Blizzara -- {2} -- Target creature's owner puts it on their choice of the top or bottom of their library.
        this.getSpellAbility().addMode(new Mode(new PutOnTopOrBottomLibraryTargetEffect(false))
                .addTarget(new TargetCreaturePermanent())
                .withCost(new GenericManaCost(2))
                .withFlavorWord("Blizzara"));

        // * Blizzaga -- {5}{U} -- Target creature's owner shuffles it into their library.
        this.getSpellAbility().addMode(new Mode(new ShuffleIntoLibraryTargetEffect()
                .setText("target creature's owner shuffles it into their library"))
                .addTarget(new TargetCreaturePermanent())
                .withCost(new ManaCostsImpl<>("{5}{U}"))
                .withFlavorWord("Blizzaga"));
    }

    private IceMagic(final IceMagic card) {
        super(card);
    }

    @Override
    public IceMagic copy() {
        return new IceMagic(this);
    }
}
