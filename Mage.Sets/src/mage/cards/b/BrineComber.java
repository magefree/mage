package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.other.AuraSpellPredicate;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrineComber extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Aura spell");

    static {
        filter.add(AuraSpellPredicate.instance);
    }

    public BrineComber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.b.BrineboundGift.class;

        // Whenever Brine Comber enters the battlefield or becomes the target of an Aura spell, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new OrTriggeredAbility(Zone.ALL, new CreateTokenEffect(new SpiritWhiteToken()), false,
                "Whenever {this} enters the battlefield or becomes the target of an Aura spell, ",
                new EntersBattlefieldTriggeredAbility(null),
                new BecomesTargetSourceTriggeredAbility(null, filter)));

        // Disturb {W}{U}
        this.addAbility(new DisturbAbility(this, "{W}{U}"));
    }

    private BrineComber(final BrineComber card) {
        super(card);
    }

    @Override
    public BrineComber copy() {
        return new BrineComber(this);
    }
}
