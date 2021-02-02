
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.ExtortAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class TreasuryThrull extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact, creature, or enchantment card from your graveyard");
    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                                 CardType.CREATURE.getPredicate(),
                                 CardType.ENCHANTMENT.getPredicate()));
    }

    public TreasuryThrull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{B}");
        this.subtype.add(SubType.THRULL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Extort (Whenever you cast a spell, you may pay {WB}. If you do, each opponent loses 1 life and you gain that much life.)
        this.addAbility(new ExtortAbility());

        // Whenever Treasury Thrull attacks, you may return target artifact, creature, or enchantment card from your graveyard to your hand.
        Ability ability = new AttacksTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private TreasuryThrull(final TreasuryThrull card) {
        super(card);
    }

    @Override
    public TreasuryThrull copy() {
        return new TreasuryThrull(this);
    }
}
