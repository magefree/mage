
package mage.cards.k;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public final class KorDuelist extends CardImpl {

    private static final String ruleText = "As long as Kor Duelist is equipped, it has double strike";

    public KorDuelist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        GainAbilitySourceEffect effect = new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(effect, new SourceIsEquiped(), ruleText)));
    }

    private KorDuelist(final KorDuelist card) {
        super(card);
    }

    @Override
    public KorDuelist copy() {
        return new KorDuelist(this);
    }
}

class SourceIsEquiped implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            List<UUID> attachments = permanent.getAttachments();
            for (UUID attachmentUUID : attachments) {
                Permanent attachment = game.getPermanent(attachmentUUID);
                if (attachment != null) {
                    if (attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
