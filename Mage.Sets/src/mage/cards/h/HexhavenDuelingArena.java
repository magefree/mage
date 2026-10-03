package mage.cards.h;

import java.util.UUID;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.BecomePreparedTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackedThisTurnPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class HexhavenDuelingArena extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature that attacked this turn");

    static {
        filter.add(AttackedThisTurnPredicate.instance);
    }

    public HexhavenDuelingArena(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: Target creature that attacked this turn becomes prepared. Activate only as a sorcery.
        ActivatedAbility ability = new ActivateAsSorceryActivatedAbility(new BecomePreparedTargetEffect(true), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {4}, {T}: Target creature becomes prepared.
        ability = new SimpleActivatedAbility(new BecomePreparedTargetEffect(true), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HexhavenDuelingArena(final HexhavenDuelingArena card) {
        super(card);
    }

    @Override
    public HexhavenDuelingArena copy() {
        return new HexhavenDuelingArena(this);
    }
}
