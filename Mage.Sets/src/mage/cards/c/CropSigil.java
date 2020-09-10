package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.hint.common.DeliriumHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.events.GameEvent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author fireshoes
 */
public final class CropSigil extends CardImpl {

    private static final FilterCard filterCreature = new FilterCard("creature card in a graveyard");
    private static final FilterCard filterLand = new FilterCard("land card in a graveyard");

    static {
        filterCreature.add(CardType.CREATURE.getPredicate());
        filterLand.add(CardType.LAND.getPredicate());
    }

    public CropSigil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // At the beginning of your upkeep, you may put the top card of your library into your graveyard.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new MillCardsControllerEffect(1), true));

        // <i>Delirium</i> &mdash; {2}{G}, Sacrifice Crop Sigil: Return up to one target creature card and up to one target land card from your graveyard to your hand.
        // Activate this ability only if there are four or more card types among cards in your graveyard.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(true), new ManaCostsImpl<>("{2}{G}"),
                DeliriumCondition.instance,
                "<i>Delirium</i> &mdash; {2}{G}, Sacrifice {this}: Return up to one target creature card and up to one target land card from your graveyard to your hand. "
                        + "Activate this ability only if there are four or more card types among cards in your graveyard");
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filterCreature));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, filterLand));
        ability.addHint(DeliriumHint.instance);
        this.addAbility(ability);
    }

    public CropSigil(final CropSigil card) {
        super(card);
    }

    @Override
    public CropSigil copy() {
        return new CropSigil(this);
    }
}
