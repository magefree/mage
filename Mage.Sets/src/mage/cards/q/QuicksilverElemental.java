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
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.ManaPoolItem;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class QuicksilverElemental extends CardImpl {

    public QuicksilverElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add("Elemental");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {U}: Quicksilver Elemental gains all activated abilities of target creature until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new QuicksilverElementalEffect(), new ManaCostsImpl("{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // You may spend blue mana as though it were mana of any color to pay the activation costs of Quicksilver Elemental's abilities.
        QuickSilverElementalBlueManaEffect effect2 = new QuickSilverElementalBlueManaEffect();
        effect2.setTargetPointer(new FixedTarget(this.getId()));
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, effect2);
        this.addAbility(ability2);
    }

    public QuicksilverElemental(final QuicksilverElemental card) {
        super(card);
    }

    @Override
    public QuicksilverElemental copy() {
        return new QuicksilverElemental(this);
    }
}

class QuicksilverElementalEffect extends ContinuousEffectImpl {

    public QuicksilverElementalEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} gains all activated abilities of target creature until end of turn";
    }

    public QuicksilverElementalEffect(final QuicksilverElementalEffect effect) {
        super(effect);
    }

    @Override
    public QuicksilverElementalEffect copy() {
        return new QuicksilverElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Permanent creature = game.getPermanent(source.getTargets().getFirstTarget());

        if (permanent != null && creature != null) {
            for (ActivatedAbility ability : creature.getAbilities().getActivatedAbilities(Zone.BATTLEFIELD)) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
        }
        return false;
    }
}

class QuickSilverElementalBlueManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public QuickSilverElementalBlueManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "You may spend blue mana as though it were mana of any color to pay the activation costs of {this}'s abilities";
    }

    public QuickSilverElementalBlueManaEffect(final QuickSilverElementalBlueManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public QuickSilverElementalBlueManaEffect copy() {
        return new QuickSilverElementalBlueManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(getTargetPointer().getFirst(game, source))) {
            if (affectedControllerId.equals(source.getControllerId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        if (mana.getBlue() > 0) {
            return ManaType.BLUE;
        }
        return null;
    }
}
