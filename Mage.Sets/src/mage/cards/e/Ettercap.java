package mage.cards.e;

import mage.MageInt;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ettercap extends AdventureCard {

    public Ettercap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{4}{G}", "Web Shot", "{2}{G}");

        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Web Shot
        // Destroy target creature with flying.
        this.getSpellCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));

        this.finalizeAdventure();
    }

    private Ettercap(final Ettercap card) {
        super(card);
    }

    @Override
    public Ettercap copy() {
        return new Ettercap(this);
    }
}
