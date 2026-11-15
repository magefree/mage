package mage.cards.f;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author muz
 */
public final class FulminousForte extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("creature and planeswalker your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public FulminousForte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");


        // Choose one --
        // * Fulminous Forte deals 1 damage to each creature and planeswalker your opponents control.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter));

        // * Fulminous Forte deals 5 damage to target creature or planeswalker.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(5)).addTarget(new TargetCreatureOrPlaneswalker()));
    }

    private FulminousForte(final FulminousForte card) {
        super(card);
    }

    @Override
    public FulminousForte copy() {
        return new FulminousForte(this);
    }
}
