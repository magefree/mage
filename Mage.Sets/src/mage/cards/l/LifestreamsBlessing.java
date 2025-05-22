package mage.cards.l;

import java.util.Optional;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 * @author balazskristof
 */
public final class LifestreamsBlessing extends CardImpl {

    private static final DynamicValue lifeValue = new MultipliedValue(GreatestPowerAmongControlledCreaturesValue.instance, 2);

    public LifestreamsBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{G}{G}");
        

        // Draw X cards, where X is the greatest power among creatures you controlled as you cast this spell. If this spell was cast from exile, you gain twice X life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(GreatestPowerAmongControlledCreaturesValue.instance)
                .setText("Draw X cards, where X is the greatest power among creatures you controlled as you cast this spell."
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new GainLifeEffect(lifeValue), LifestreamsBlessingCondition.instance,
                "If this spell was cast from exile, you gain twice X life."
        ));
        this.getSpellAbility().addHint(GreatestPowerAmongControlledCreaturesValue.getHint());
        
        // Foretell {4}{G}
        this.addAbility(new ForetellAbility(this, "{4}{G}"));

    }

    private LifestreamsBlessing(final LifestreamsBlessing card) {
        super(card);
    }

    @Override
    public LifestreamsBlessing copy() {
        return new LifestreamsBlessing(this);
    }
}

enum LifestreamsBlessingCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Ability::getSourceId)
                .map(game::getSpell)
                .map(Spell::getFromZone)
                .map(Zone.EXILED::match)
                .orElse(false);
    }
}
