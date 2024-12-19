package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArbiterOfWoe extends CardImpl {

    public ArbiterOfWoe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, each opponent discards a card and loses 2 life. You draw a card and gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT));
        ability.addEffect(new LoseLifeOpponentsEffect(2).setText("and loses 2 life"));
        ability.addEffect(new DrawCardSourceControllerEffect(1, true));
        ability.addEffect(new GainLifeEffect(2).setText("and gain 2 life"));
        this.addAbility(ability);
    }

    private ArbiterOfWoe(final ArbiterOfWoe card) {
        super(card);
    }

    @Override
    public ArbiterOfWoe copy() {
        return new ArbiterOfWoe(this);
    }
}
