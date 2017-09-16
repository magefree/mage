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
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.other.AuraCardCanAttachToPermanentId;
import mage.filter.predicate.other.OwnerPredicate;
import mage.filter.predicate.permanent.AttachedToPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public class HakimLoreweaver extends CardImpl {

    private final static String rule = "Return target Aura card from your graveyard to the battlefield attached to Hakim, Loreweaver. Activate this ability only during your upkeep and only if Hakim isn't enchanted.";
    private static final FilterCard filter = new FilterCard("target Aura card from your graveyard");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filter.add(new SubtypePredicate(SubType.AURA));
        filter.add(new OwnerPredicate(TargetController.YOU));
    }

    public HakimLoreweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {U}{U}: Return target Aura card from your graveyard to the battlefield attached to Hakim, Loreweaver. Activate this ability only during your upkeep and only if Hakim isn't enchanted.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new HakimLoreweaverEffect(), new ManaCostsImpl("{U}{U}"), new HakimLoreweaverCondition(), rule);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // {U}{U}, {tap}: Destroy all Auras attached to Hakim.
        FilterPermanent filterAurasOnHakim = new FilterPermanent("Auras attached to Hakim");
        filterAurasOnHakim.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filterAurasOnHakim.add(new SubtypePredicate(SubType.AURA));
        FilterPermanent filterSourceId = new FilterPermanent();
        filterSourceId.add(new CardIdPredicate(this.getId()));
        filterAurasOnHakim.add(new AttachedToPredicate(filterSourceId));
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyAllEffect(filterAurasOnHakim), new ManaCostsImpl("{U}{U}"));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);

    }

    public HakimLoreweaver(final HakimLoreweaver card) {
        super(card);
    }

    @Override
    public HakimLoreweaver copy() {
        return new HakimLoreweaver(this);
    }
}

class HakimLoreweaverEffect extends OneShotEffect {

    public HakimLoreweaverEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return target Aura card from your graveyard to the battlefield attached to {this}. Activate this ability only during your upkeep and only if Hakim isn't enchanted";
    }

    public HakimLoreweaverEffect(final HakimLoreweaverEffect effect) {
        super(effect);
    }

    @Override
    public HakimLoreweaverEffect copy() {
        return new HakimLoreweaverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent hakimLoreweaver = game.getPermanent(source.getSourceId());
        Card targetAuraCard = game.getCard(source.getFirstTarget());
        if (controller != null
                && hakimLoreweaver != null
                && controller.canRespond()
                && targetAuraCard != null
                && new AuraCardCanAttachToPermanentId(hakimLoreweaver.getId()).apply(targetAuraCard, game)) {
            Target target = targetAuraCard.getSpellAbility().getTargets().get(0);
            if (target != null) {
                game.getState().setValue("attachTo:" + targetAuraCard.getId(), hakimLoreweaver);
                controller.moveCards(targetAuraCard, Zone.BATTLEFIELD, source, game);
                return hakimLoreweaver.addAttachment(targetAuraCard.getId(), game);
            }
        }
        return false;
    }
}

class HakimLoreweaverCondition implements Condition {

    static private final FilterPermanent auras = new FilterPermanent();

    static {
        auras.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent hakimLoreweaver = game.getPermanent(source.getSourceId());
        if (hakimLoreweaver != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(auras, game)) {
                if (permanent != null
                        && hakimLoreweaver.getAttachments().contains(permanent.getId())) {
                    return false;
                }
            }
            return PhaseStep.UPKEEP == game.getStep().getType()
                    && game.getActivePlayerId().equals(source.getControllerId());
        }
        return false;
    }
}
