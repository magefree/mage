
package mage.cards.g;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class GiantsIre extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Giant");

    static {
        filter.add(SubType.GIANT.getPredicate());
    }

    public GiantsIre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.SORCERY}, "{3}{R}");
        this.subtype.add(SubType.GIANT);

        // Giant's Ire deals 4 damage to target player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());

        // If you control a Giant, draw a card.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1),
                new PermanentsOnTheBattlefieldCondition(filter), "If you control a Giant, draw a card"));
    }

    private GiantsIre(final GiantsIre card) {
        super(card);
    }

    @Override
    public GiantsIre copy() {
        return new GiantsIre(this);
    }
}
