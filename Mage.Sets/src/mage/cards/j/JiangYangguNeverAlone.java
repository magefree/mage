package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.MowuToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class JiangYangguNeverAlone extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("tokens you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public JiangYangguNeverAlone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Jiang Yanggu enters, create Mowu, a legendary 3/3 green Dog creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MowuToken())));

        // At the beginning of your end step, untap all tokens you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new UntapAllEffect(filter)));
    }

    private JiangYangguNeverAlone(final JiangYangguNeverAlone card) {
        super(card);
    }

    @Override
    public JiangYangguNeverAlone copy() {
        return new JiangYangguNeverAlone(this);
    }
}
