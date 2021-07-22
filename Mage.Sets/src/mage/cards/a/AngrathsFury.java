
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class AngrathsFury extends CardImpl {

    private static final FilterCard filter = new FilterCard("Angrath, Minotaur Pirate");

    static {
        filter.add(new NamePredicate(filter.getMessage()));
    }

    public AngrathsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{R}");

        // Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Angrath's Fury deals 3 damage to target player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3).setTargetPointer(new SecondTargetPointer())
                .setText("{this} deals 3 damage to target player or planeswalker"));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());

        // You may search your library and/or graveyard for a card named Angrath, Minotaur Pirate, reveal it, and put it into your hand.  If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter)
                .setText("You may search your library and/or graveyard for a card named Angrath, Minotaur Pirate, reveal it, and put it into your hand. If you search your library this way, shuffle"));

    }

    private AngrathsFury(final AngrathsFury card) {
        super(card);
    }

    @Override
    public AngrathsFury copy() {
        return new AngrathsFury(this);
    }
}
