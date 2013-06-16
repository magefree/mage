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
package mage.sets.planechase2012;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public class VelaTheNightClad extends CardImpl<VelaTheNightClad> {

    private final static String rule = "Whenever {this} or another creature you control leaves the battlefield, ";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public VelaTheNightClad(UUID ownerId) {
        super(ownerId, 107, "Vela the Night-Clad", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");
        this.expansionSetCode = "PC2";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());
        // Other creatures you control have intimidate.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(IntimidateAbility.getInstance(),
                    Duration.WhileOnBattlefield, new FilterCreaturePermanent("Other creatures you control"), true )));
        // Whenever Vela the Night-Clad or another creature you control leaves the battlefield, each opponent loses 1 life.
        Ability ability = new ZoneChangeAllTriggeredAbility(
                Zone.BATTLEFIELD,
                Zone.BATTLEFIELD, null,
                new LoseLifeOpponentsEffect(1),
                filter, rule, false);
        this.addAbility(ability);
    }

    public VelaTheNightClad(final VelaTheNightClad card) {
        super(card);
    }

    @Override
    public VelaTheNightClad copy() {
        return new VelaTheNightClad(this);
    }
}
