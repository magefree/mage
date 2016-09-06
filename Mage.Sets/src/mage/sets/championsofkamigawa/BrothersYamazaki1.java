/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class BrothersYamazaki1 extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new NamePredicate("Brothers Yamazaki"));
    }

    public BrothersYamazaki1(UUID ownerId) {
        super(ownerId, "160a", "Brothers Yamazaki", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Samurai");
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Bushido 1
        this.addAbility(new BushidoAbility(1));

        // If there are exactly two permanents named Brothers Yamazaki on the battlefield, the "legend rule" doesn't apply to them.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BrothersYamazakiIgnoreLegendRuleEffectEffect()));

        // Each other creature named Brothers Yamazaki gets +2/+2 and has haste.
        Effect effect = new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, filter, true);
        effect.setText("Each other creature named Brothers Yamazaki gets +2/+2");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("and has haste");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public BrothersYamazaki1(final BrothersYamazaki1 card) {
        super(card);
    }

    @Override
    public BrothersYamazaki1 copy() {
        return new BrothersYamazaki1(this);
    }
}

class BrothersYamazakiIgnoreLegendRuleEffectEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new NamePredicate("Brothers Yamazaki"));
    }

    public BrothersYamazakiIgnoreLegendRuleEffectEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "If there are exactly two permanents named Brothers Yamazaki on the battlefield, the \"legend rule\" doesn't apply to them";
    }

    public BrothersYamazakiIgnoreLegendRuleEffectEffect(final BrothersYamazakiIgnoreLegendRuleEffectEffect effect) {
        super(effect);
    }

    @Override
    public BrothersYamazakiIgnoreLegendRuleEffectEffect copy() {
        return new BrothersYamazakiIgnoreLegendRuleEffectEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT_BY_LEGENDARY_RULE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.getName().equals("Brothers Yamazaki")) {
            return game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) == 2;
        }
        return false;
    }

}
