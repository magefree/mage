package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.AttachToSourceTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class WinterSoldierIcyAssassin extends CardImpl {

    public WinterSoldierIcyAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Winter Soldier gets +2/+0 for each Equipment attached to him.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
            new EquipmentAttachedCount(2), StaticValue.get(0), Duration.WhileOnBattlefield
        ).setText("Winter Soldier gets +2/+0 for each Equipment attached to him")));

        // {3}{W}{B}: Return this card from your graveyard to the battlefield with a finality counter on him.
        // Then you may attach an Equipment you control to him.
        Ability ability = new SimpleActivatedAbility(
            Zone.GRAVEYARD,
            new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(
                CounterType.FINALITY.createInstance(), false
            ).withAdditionalEffect(new OneShotNonTargetEffect(
                new AttachToSourceTargetEffect().setText("you may attach an Equipment you control to him"),
                new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT)
                    .withChooseHint("to attach to Winter Soldier")
            ).concatBy("Then")
            ).setText("return this card from your graveyard to the battlefield with a finality counter on him"),
            new ManaCostsImpl<>("{3}{W}{B}")
        );
        this.addAbility(ability);
    }

    private WinterSoldierIcyAssassin(final WinterSoldierIcyAssassin card) {
        super(card);
    }

    @Override
    public WinterSoldierIcyAssassin copy() {
        return new WinterSoldierIcyAssassin(this);
    }
}
