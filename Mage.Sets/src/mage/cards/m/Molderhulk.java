package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Molderhulk extends CardImpl {

    private static final FilterCard filter
            = new FilterLandCard("land card from your graveyard");

    public Molderhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{B}{G}");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Undergrowth — This spell costs {1} less to cast for each creature card in your graveyard.
        DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue));
        ability.setAbilityWord(AbilityWord.UNDERGROWTH);
        ability.setRuleAtTheTop(true);
        ability.addHint(new ValueHint("Creature card in your graveyard", xValue));
        this.addAbility(ability);

        // When Molderhulk enters the battlefield, return target land card from your graveyard to the battlefield.
        ability = new EntersBattlefieldTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private Molderhulk(final Molderhulk card) {
        super(card);
    }

    @Override
    public Molderhulk copy() {
        return new Molderhulk(this);
    }
}
