package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CharmingScoundrel extends CardImpl {

    public CharmingScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Charming Scoundrel enters the battlefield, choose one —
        // * Discard a card, then draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardControllerEffect(1));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));

        // * Create a Treasure token.
        ability.addMode(new Mode(new CreateTokenEffect(new TreasureToken())));

        // * Create a Wicked Role token attached to target creature you control.
        Mode mode = new Mode(new CreateRoleAttachedTargetEffect(RoleType.WICKED));
        mode.addTarget(new TargetControlledCreaturePermanent());
        ability.addMode(mode);

        this.addAbility(ability);
    }

    private CharmingScoundrel(final CharmingScoundrel card) {
        super(card);
    }

    @Override
    public CharmingScoundrel copy() {
        return new CharmingScoundrel(this);
    }
}
