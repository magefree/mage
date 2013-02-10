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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedCondition;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTokenEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterEnchantment;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class KitsuneMystic extends CardImpl<KitsuneMystic> {

    public KitsuneMystic(UUID ownerId) {
        super(ownerId, 28, "Kitsune Mystic", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Fox");
        this.subtype.add("Wizard");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.flipCard = true;
        this.flipCardName = "Autumn-Tail, Kitsune Sage";

        // At the beginning of the end step, if Kitsune Mystic is enchanted by two or more Auras, flip it.
        this.addAbility(new ConditionalTriggeredAbility(
                new OnEventTriggeredAbility(GameEvent.EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new FlipSourceEffect()),
                new EnchantedCondition(2),"At the beginning of the end step, if Kitsune Mystic is enchanted by two or more Auras, flip it.", false
                ));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(new CopyTokenEffect(new AutumnTailKitsuneSage()), FlippedCondition.getInstance(), "")));
    }

    public KitsuneMystic(final KitsuneMystic card) {
        super(card);
    }

    @Override
    public KitsuneMystic copy() {
        return new KitsuneMystic(this);
    }
}

class AutumnTailKitsuneSage extends Token {

    private static final FilterEnchantment filter = new FilterEnchantment("Aura attached to a creature");

    static {
        filter.add(new AttachmentAttachedToCardTypePredicate(CardType.CREATURE));
        filter.add(new SubtypePredicate("Aura"));
    }

    AutumnTailKitsuneSage() {
        super("Autumn-Tail, Kitsune Sage", "");
        supertype.add("Legendary");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add("Fox");
        subtype.add("Wizard");
        power = new MageInt(4);
        toughness = new MageInt(5);

        // {1}: Attach target Aura attached to a creature to another creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AutumnTailEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetPermanent(filter));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }
}

class AttachmentAttachedToCardTypePredicate implements Predicate<Permanent> {

    private final CardType cardType;

    public AttachmentAttachedToCardTypePredicate(CardType cardType) {
        this.cardType = cardType;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        if (input.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(input.getAttachedTo());
            if (attachedTo != null && attachedTo.getCardType().contains(cardType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "AttachmentAttachedToCardType(" + cardType + ')';
    }
}

class AutumnTailEffect extends OneShotEffect<AutumnTailEffect> {

    public AutumnTailEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Attach target Aura attached to a creature to another creature";
    }

    public AutumnTailEffect(final AutumnTailEffect effect) {
        super(effect);
    }

    @Override
    public AutumnTailEffect copy() {
        return new AutumnTailEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanent(source.getFirstTarget());
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (aura != null && creature != null) {
            Permanent oldCreature = game.getPermanent(aura.getAttachedTo());
            if (oldCreature == null || oldCreature.equals(creature)) {
                return false;
            }
            if (oldCreature.removeAttachment(aura.getId(), game)) {
                return creature.addAttachment(aura.getId(), game);
            }
        }
        return false;
    }
}
