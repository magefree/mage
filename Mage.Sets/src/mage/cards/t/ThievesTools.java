package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThievesTools extends CardImpl {

    public ThievesTools(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Thieves' Tools enters the battlefield, create a Treasure token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Equipped creature can't be blocked as long as its power is 3 or less.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedAttachedEffect(AttachmentType.EQUIPMENT), ThievesToolsCondition.instance,
                "equipped creature can't be blocked as long as its power is 3 or less"
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private ThievesTools(final ThievesTools card) {
        super(card);
    }

    @Override
    public ThievesTools copy() {
        return new ThievesTools(this);
    }
}

enum ThievesToolsCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        Permanent equipped = game.getPermanent(permanent.getAttachedTo());
        return equipped != null && equipped.getPower().getValue() <= 3;
    }
}
