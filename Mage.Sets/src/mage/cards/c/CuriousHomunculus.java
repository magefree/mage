package mage.cards.c;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CuriousHomunculus extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(3, new FilterInstantOrSorceryCard("instant and/or sorcery cards"));
    private static final Hint hint = new ValueHint("Instant and sorcery cards in your graveyard", new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));

    public CuriousHomunculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.v.VoraciousReader.class;

        // {T}: Add {C}. Spend this mana only to cast an instant or sorcery spell.
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 1, new InstantOrSorcerySpellManaBuilder()));

        // At the beginning of your upkeep, if there are three or more instant and/or sorcery cards in your graveyard, transform Curious Homunculus.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect()).withInterveningIf(condition).addHint(hint));
    }

    private CuriousHomunculus(final CuriousHomunculus card) {
        super(card);
    }

    @Override
    public CuriousHomunculus copy() {
        return new CuriousHomunculus(this);
    }
}
