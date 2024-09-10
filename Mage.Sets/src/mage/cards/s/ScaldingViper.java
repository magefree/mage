package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScaldingViper extends AdventureCard {

    private static final FilterSpell filter = new FilterSpell("a spell with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public ScaldingViper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{R}", "Steam Clean", "{1}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever an opponent casts a spell with mana value 3 or less, Scalding Viper deals 1 damage to that player.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(1, true, "that player"),
                filter, false, SetTargetPointer.PLAYER
        ));

        // Steam Clean
        // Return target nonland permanent to its owner's hand.
        this.getSpellCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetNonlandPermanent());

        this.finalizeAdventure();
    }

    private ScaldingViper(final ScaldingViper card) {
        super(card);
    }

    @Override
    public ScaldingViper copy() {
        return new ScaldingViper(this);
    }
}
