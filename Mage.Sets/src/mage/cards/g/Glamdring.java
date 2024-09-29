package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author bobby-mccann
 */
public final class Glamdring extends CardImpl {

    public Glamdring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has first strike and gets +1/+0 for each instant and sorcery card in your graveyard.
        Effect firstStrike = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText("Equipped creature has first strike");
        Effect boost = new BoostEquippedEffect(
                new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY), StaticValue.get(0))
                .setText(" and gets +1/+0 for each instant and sorcery card in your graveyard.");

        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, firstStrike);
        ability.addEffect(boost);
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, you may cast an instant or sorcery spell from your hand with mana value less than or equal to that damage without paying its mana cost.
        this.addAbility(
                new DealsDamageToAPlayerAttachedTriggeredAbility(
                        new GlamdringEffect(),
                        "equipped creature", false
                )
        );

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private Glamdring(final Glamdring card) {
        super(card);
    }

    @Override
    public Glamdring copy() {
        return new Glamdring(this);
    }
}

class GlamdringEffect extends OneShotEffect {
    GlamdringEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast an instant or sorcery spell from your hand with mana value less than or equal to that damage without paying its mana cost";
    }

    private GlamdringEffect(final GlamdringEffect effect) {
        super(effect);
    }

    @Override
    public GlamdringEffect copy() {
        return new GlamdringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard filter = new FilterInstantOrSorceryCard("an instant or sorcery spell from your hand with mana value less than or equal to that damage");
        filter.add(new ManaValuePredicate(
                ComparisonType.OR_LESS, SavedDamageValue.MUCH.calculate(game, source, this)
        ));
        return new CastFromHandForFreeEffect(filter).apply(game, source);
    }
}
