package mage.cards.s;

import mage.MageInt;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DauntAbility;
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
public final class StormkeldVanguard extends AdventureCard {

    public StormkeldVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{4}{G}{G}", "Bear Down", "{1}{G}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Stormkeld Vanguard can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Bear Down
        // Destroy target artifact or enchantment.
        this.getSpellCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        this.finalizeAdventure();
    }

    private StormkeldVanguard(final StormkeldVanguard card) {
        super(card);
    }

    @Override
    public StormkeldVanguard copy() {
        return new StormkeldVanguard(this);
    }
}
