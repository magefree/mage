
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class OjutaisCommand extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 2 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public OjutaisCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{U}");

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Return target creature card with converted mana cost 2 or less from your graveyard to the battlefield;
        this.getSpellAbility().getEffects().add(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().getTargets().add(new TargetCardInYourGraveyard(filter));

        // or You gain 4 life;
        Mode mode = new Mode();
        mode.addEffect(new GainLifeEffect(4));
        this.getSpellAbility().getModes().addMode(mode);

        // or Counter target creature spell;
        mode = new Mode();
        mode.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
        mode.addEffect(new CounterTargetEffect());
        this.getSpellAbility().getModes().addMode(mode);

        // or Draw a card
        mode = new Mode();
        mode.addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().getModes().addMode(mode);
    }

    private OjutaisCommand(final OjutaisCommand card) {
        super(card);
    }

    @Override
    public OjutaisCommand copy() {
        return new OjutaisCommand(this);
    }
}
