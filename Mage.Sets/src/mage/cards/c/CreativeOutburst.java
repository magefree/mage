package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CreativeOutburst extends CardImpl {

    public CreativeOutburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}{R}{R}");

        // Creative Outburst deals 5 damage to any target. Look at the top five cards of your library. Put one of them into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(5, 1, PutCards.HAND, PutCards.BOTTOM_RANDOM));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // {U/R}{U/R}, Discard Creative Outburst: Create a Treasure token.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND, new CreateTokenEffect(new TreasureToken()), new ManaCostsImpl("{U/R}{U/R}")
        );
        ability.addCost(new DiscardSourceCost());
        this.addAbility(ability);
    }

    private CreativeOutburst(final CreativeOutburst card) {
        super(card);
    }

    @Override
    public CreativeOutburst copy() {
        return new CreativeOutburst(this);
    }
}
