package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;


/**
 *
 * @author jmharmon
 */

public final class SylvanHierophant extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("another target creature card from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SylvanHierophant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Sylvan Hierophant dies, exile Sylvan Hierophant, then return another target creature card from your graveyard to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        Ability ability = new DiesSourceTriggeredAbility(new ExileSourceEffect(), false);
        ability.addEffect(effect);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private SylvanHierophant(final SylvanHierophant card) {
        super(card);
    }

    @Override
    public SylvanHierophant copy() {
        return new SylvanHierophant(this);
    }
}
