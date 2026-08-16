package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 * @author muz
 */
public final class GiantsBoulder extends CardImpl {

    public GiantsBoulder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // When this artifact enters, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // {1}, {T}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {7}, {T}, Sacrifice this artifact: Destroy target permanent.
        ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(7));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private GiantsBoulder(final GiantsBoulder card) {
        super(card);
    }

    @Override
    public GiantsBoulder copy() {
        return new GiantsBoulder(this);
    }
}
