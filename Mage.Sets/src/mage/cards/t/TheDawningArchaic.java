package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CastManaAdjustment;

/**
 *
 * @author muz
 */
public final class TheDawningArchaic extends CardImpl {

    public TheDawningArchaic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // This spell costs {1} less to cast for each instant and sorcery card in your graveyard.
        DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue));
        ability.setRuleAtTheTop(true);
        ability.addHint(new ValueHint("Instant and sorcery card in your graveyard", xValue));
        this.addAbility(ability);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever The Dawning Archaic attacks, you may cast target instant or sorcery card from your graveyard without paying its mana cost. If that spell would be put into your graveyard, exile it instead.
        Ability ability2 = new AttacksTriggeredAbility(new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST, true), false);
        ability2.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability2);
    }

    private TheDawningArchaic(final TheDawningArchaic card) {
        super(card);
    }

    @Override
    public TheDawningArchaic copy() {
        return new TheDawningArchaic(this);
    }
}
