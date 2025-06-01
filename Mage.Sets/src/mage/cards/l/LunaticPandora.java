package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LunaticPandora extends CardImpl {

    public LunaticPandora(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.supertype.add(SuperType.LEGENDARY);

        // {2}, {T}: Surveil 1.
        Ability ability = new SimpleActivatedAbility(new SurveilEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {6}, {T}, Sacrifice Lunatic Pandora: Destroy target nonland permanent.
        ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private LunaticPandora(final LunaticPandora card) {
        super(card);
    }

    @Override
    public LunaticPandora copy() {
        return new LunaticPandora(this);
    }
}
