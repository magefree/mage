package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOneOrMoreTriggeredAbility;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiritcallEnthusiast extends PrepareCard {

    public SpiritcallEnthusiast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}", "Scrollboost", new CardType[]{CardType.SORCERY}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever one or more tokens you control enter, this creature becomes prepared.
        this.addAbility(new EntersBattlefieldOneOrMoreTriggeredAbility(
                new BecomePreparedSourceEffect(), StaticFilters.FILTER_PERMANENT_TOKENS, TargetController.YOU
        ));

        // Scrollboost
        // Sorcery {1}{W}
        // One or two target creatures each get +2/+2 until end of turn.
        this.getSpellCard().getSpellAbility().addEffect(new BoostTargetEffect(2, 2));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent(1, 2));
    }

    private SpiritcallEnthusiast(final SpiritcallEnthusiast card) {
        super(card);
    }

    @Override
    public SpiritcallEnthusiast copy() {
        return new SpiritcallEnthusiast(this);
    }
}
