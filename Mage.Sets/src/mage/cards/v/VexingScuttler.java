
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class VexingScuttler extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public VexingScuttler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.CRAB);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Emerge {6}{U}
        this.addAbility(new EmergeAbility(this, new ManaCostsImpl<>("{6}{U}")));

        // When you cast Vexing Scuttler, you may return target instant or sorcery card from your graveyard to your hand.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("you may return target instant or sorcery card from your graveyard to your hand");
        Ability ability = new CastSourceTriggeredAbility(effect, true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private VexingScuttler(final VexingScuttler card) {
        super(card);
    }

    @Override
    public VexingScuttler copy() {
        return new VexingScuttler(this);
    }
}
