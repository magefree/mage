
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class OjutaiExemplars extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a noncreature spell");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public OjutaiExemplars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast a noncreature spell, choose one - Tap target creature; 
        Ability ability = new SpellCastControllerTriggeredAbility(new TapTargetEffect(), filter, false);
        ability.addTarget(new TargetCreaturePermanent());

        // Ojutai Exemplars gain first strike and lifelink until end of turn; 
        Mode mode = new Mode();
        Effect effect = new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("{this} gains first strike");
        mode.addEffect(effect);
        Effect effect2 = new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect2.setText("and lifelink until end of turn");
        mode.addEffect(effect2);
        ability.addMode(mode);

        // or Exile Ojutai Exemplars, then return it to the battlefield tapped under its owner's control.
        mode = new Mode();
        mode.addEffect(new OjutaiExemplarsEffect());
        ability.addMode(mode);

        this.addAbility(ability);
    }

    public OjutaiExemplars(final OjutaiExemplars card) {
        super(card);
    }

    @Override
    public OjutaiExemplars copy() {
        return new OjutaiExemplars(this);
    }
}

class OjutaiExemplarsEffect extends OneShotEffect {

    public OjutaiExemplarsEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile {this}, then return it to the battlefield tapped under its owner's control";
    }

    public OjutaiExemplarsEffect(final OjutaiExemplarsEffect effect) {
        super(effect);
    }

    @Override
    public OjutaiExemplarsEffect copy() {
        return new OjutaiExemplarsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent ojutaiExemplars = game.getPermanent(source.getSourceId());
        if (ojutaiExemplars != null) {
            if (ojutaiExemplars.moveToExile(source.getSourceId(), "Ojutai Exemplars", source.getSourceId(), game)) {
                Card card = game.getExile().getCard(source.getSourceId(), game);
                if (card != null) {
                    return card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, true);
                }
            }
        }
        return false;
    }
}
