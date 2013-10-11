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
package mage.sets.fifthdawn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class HelmOfKaldra extends CardImpl<HelmOfKaldra> {
    public static final FilterControlledArtifactPermanent filterHelm = new FilterControlledArtifactPermanent();
    public static final FilterControlledArtifactPermanent filterShield = new FilterControlledArtifactPermanent();
    public static final FilterControlledArtifactPermanent filterSword = new FilterControlledArtifactPermanent();

    static {
        filterHelm.add(new NamePredicate("Helm of Kaldra"));
        filterShield.add(new NamePredicate("Shield of Kaldra"));
        filterSword.add(new NamePredicate("Sword of Kaldra"));
    }

    public HelmOfKaldra(UUID ownerId) {
        super(ownerId, 131, "Helm of Kaldra", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "5DN";
        this.supertype.add("Legendary");
        this.subtype.add("Equipment");

        // Equipped creature has first strike, trample, and haste.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT));
        Effect effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText(", trample");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.EQUIPMENT);
        effect.setText(" and haste");
        ability.addEffect(effect);
        this.addAbility(ability);
        // {1}: If you control Equipment named Helm of Kaldra, Sword of Kaldra, and Shield of Kaldra, put a legendary 4/4 colorless Avatar creature token named Kaldra onto the battlefield and attach those Equipment to it.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new HelmOfKaldraEffect(),
                new GenericManaCost(1),
                new HelmOfKaldraCondition(),
                "{1}: If you control Equipment named Helm of Kaldra, Sword of Kaldra, and Shield of Kaldra, put a legendary 4/4 colorless Avatar creature token named Kaldra onto the battlefield and attach those Equipment to it"));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.Benefit, new ManaCostsImpl("{2}")));
    }

    public HelmOfKaldra(final HelmOfKaldra card) {
        super(card);
    }

    @Override
    public HelmOfKaldra copy() {
        return new HelmOfKaldra(this);
    }
}

class HelmOfKaldraCondition implements Condition {


    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getBattlefield().count(HelmOfKaldra.filterHelm, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        if (game.getBattlefield().count(HelmOfKaldra.filterShield, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        if (game.getBattlefield().count(HelmOfKaldra.filterShield, source.getSourceId(), source.getControllerId(), game) < 1) {
            return false;
        }
        return true;
    }

}

class HelmOfKaldraEffect extends OneShotEffect<HelmOfKaldraEffect> {

    public HelmOfKaldraEffect() {
        super(Outcome.Benefit);
        this.staticText = "If you control Equipment named Helm of Kaldra, Sword of Kaldra, and Shield of Kaldra, put a legendary 4/4 colorless Avatar creature token named Kaldra onto the battlefield and attach those Equipment to it";
    }

    public HelmOfKaldraEffect(final HelmOfKaldraEffect effect) {
        super(effect);
    }

    @Override
    public HelmOfKaldraEffect copy() {
        return new HelmOfKaldraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (new HelmOfKaldraCondition().apply(game, source)) {
            CreateTokenEffect effect = new CreateTokenEffect(new KaldraToken());
            effect.apply(game, source);
            UUID kaldraId = effect.getLastAddedTokenId();
            Permanent kaldra = game.getPermanent(kaldraId);
            if (kaldra != null) {
                // Attach helm to the token
                for (Permanent kaldrasHelm :game.getBattlefield().getAllActivePermanents(HelmOfKaldra.filterHelm, source.getControllerId(), game)) {
                    kaldra.addAttachment(kaldrasHelm.getId(), game);
                    break;
                }
                // Attach shield to the token
                for (Permanent kaldrasShield :game.getBattlefield().getAllActivePermanents(HelmOfKaldra.filterShield, source.getControllerId(), game)) {
                    kaldra.addAttachment(kaldrasShield.getId(), game);
                    break;
                }
                // Attach sword to the token
                for (Permanent kaldrasSword :game.getBattlefield().getAllActivePermanents(HelmOfKaldra.filterSword, source.getControllerId(), game)) {
                    kaldra.addAttachment(kaldrasSword.getId(), game);
                    break;
                }

            }
        }
        return false;
    }
}

class KaldraToken extends Token {

    public KaldraToken() {
        super("Kaldra", "legendary 4/4 colorless Avatar creature token named Kaldra");
        supertype.add("Legendary");
        cardType.add(CardType.CREATURE);
        subtype.add("Avatar");
        power = new MageInt(4);
        toughness = new MageInt(4);
    }
}
