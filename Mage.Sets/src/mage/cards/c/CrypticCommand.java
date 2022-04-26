package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

/**
 *
 * @author jonubuu
 */
public final class CrypticCommand extends CardImpl {

    public CrypticCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}{U}");

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Counter target spell;
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // or return target permanent to its owner's hand;
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetPermanent());
        this.getSpellAbility().getModes().addMode(mode);

        // or tap all creatures your opponents control;
        mode = new Mode(new TapAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES));
        this.getSpellAbility().getModes().addMode(mode);

        // or draw a card.
        mode = new Mode(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().getModes().addMode(mode);
    }

    private CrypticCommand(final CrypticCommand card) {
        super(card);
    }

    @Override
    public CrypticCommand copy() {
        return new CrypticCommand(this);
    }
}
