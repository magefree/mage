package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.targetadjustment.XTargetsAdjuster;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class AlexiZephyrMage extends CardImpl {

    public AlexiZephyrMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {X}{U}, {tap}, Discard two cards: Return X target creatures to their owners' hands.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return X target creatures to their owner's hands");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{X}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, new FilterCard("two cards"))));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURES));
        ability.setTargetAdjuster(XTargetsAdjuster.instance);
        this.addAbility(ability);
    }

    private AlexiZephyrMage(final AlexiZephyrMage card) {
        super(card);
    }

    @Override
    public AlexiZephyrMage copy() {
        return new AlexiZephyrMage(this);
    }
}
