package mage.cards.v;

import mage.ObjectColor;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VeilOfSecrecy extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("a blue creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public VeilOfSecrecy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");
        this.subtype.add(SubType.ARCANE);

        // Target creature gains shroud until end of turn and can't be blocked this turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(ShroudAbility.getInstance()).setText("target creature gains shroud until end of turn"));
        this.getSpellAbility().addEffect(new CantBeBlockedTargetEffect().setText("and can't be blocked this turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("gains shroud and can't be blocked"));

        // Splice onto Arcane-Return a blue creature you control to its owner's hand.
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(filter))));
    }

    private VeilOfSecrecy(final VeilOfSecrecy card) {
        super(card);
    }

    @Override
    public VeilOfSecrecy copy() {
        return new VeilOfSecrecy(this);
    }
}
