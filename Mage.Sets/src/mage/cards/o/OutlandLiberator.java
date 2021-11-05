package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OutlandLiberator extends CardImpl {

    public OutlandLiberator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.f.FrenziedTrapbreaker.class;

        // {1}, Sacrifice Outland Liberator: Destroy target artifact or enchantment.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(new DayboundAbility());
    }

    private OutlandLiberator(final OutlandLiberator card) {
        super(card);
    }

    @Override
    public OutlandLiberator copy() {
        return new OutlandLiberator(this);
    }
}
