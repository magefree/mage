package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PreWarFormalwear extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
        filter.add(CardType.CREATURE.getPredicate());
    }

    public PreWarFormalwear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Pre-War Formalwear enters the battlefield, return target creature card with mana value 3 or less from your graveyard to the battlefield and attach Pre-War Formalwear to it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PreWarFormalwerEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Equipped creature gets +2/+2 and has vigilance.
        ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA).setText("and has vigilance"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private PreWarFormalwear(final PreWarFormalwear card) {
        super(card);
    }

    @Override
    public PreWarFormalwear copy() {
        return new PreWarFormalwear(this);
    }
}

class PreWarFormalwerEffect extends OneShotEffect {

    PreWarFormalwerEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return target creature card with mana value 3 or less from your graveyard "
                + "to the battlefield and attach Pre-War Formalwear to it";
    }

    private PreWarFormalwerEffect(final PreWarFormalwerEffect effect) {
        super(effect);
    }

    @Override
    public PreWarFormalwerEffect copy() {
        return new PreWarFormalwerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (card == null || player == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null) {
            new AttachEffect(Outcome.BoostCreature)
                    .setTargetPointer(new FixedTarget(permanent.getId()))
                    .apply(game, source);
        }
        return true;
    }

}
