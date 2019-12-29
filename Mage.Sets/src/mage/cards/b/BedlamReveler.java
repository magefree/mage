
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SourceCostReductionForEachCardInGraveyardEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;

/**
 *
 * @author fireshoes
 */
public final class BedlamReveler extends CardImpl {

    public BedlamReveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.subtype.add(SubType.DEVIL, SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Bedlam Reveler costs {1} less to cast for each instant or sorcery card in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SourceCostReductionForEachCardInGraveyardEffect(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Prowess
        this.addAbility(new ProwessAbility());

        // When Bedlam Reveler enters the battlefield, discard your hand, then draw three cards.
        ability = new EntersBattlefieldTriggeredAbility(new DiscardHandControllerEffect());
        Effect effect = new DrawCardSourceControllerEffect(3);
        effect.setText("then draw three cards");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public BedlamReveler(final BedlamReveler card) {
        super(card);
    }

    @Override
    public BedlamReveler copy() {
        return new BedlamReveler(this);
    }
}
