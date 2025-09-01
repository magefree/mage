package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenatHeartOfHydaelyn extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a legendary spell");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public VenatHeartOfHydaelyn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.h.HydaelynTheMothercrystal.class;

        // Whenever you cast a legendary spell, draw a card. This ability triggers only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, false
        ).setTriggersLimitEachTurn(1));

        // Hero's Sundering -- {7}, {T}: Exile target nonland permanent. Transform Venat. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        Ability ability = new ActivateAsSorceryActivatedAbility(new ExileTargetEffect(), new GenericManaCost(7));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new TransformSourceEffect());
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability.withFlavorWord("Hero's Sundering"));
    }

    private VenatHeartOfHydaelyn(final VenatHeartOfHydaelyn card) {
        super(card);
    }

    @Override
    public VenatHeartOfHydaelyn copy() {
        return new VenatHeartOfHydaelyn(this);
    }
}
