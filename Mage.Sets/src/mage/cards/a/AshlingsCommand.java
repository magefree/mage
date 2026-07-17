package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AshlingsCommand extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ELEMENTAL);

    public AshlingsCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.INSTANT}, "{3}{U}{R}");

        this.subtype.add(SubType.ELEMENTAL);

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Create a token that's a copy of target Elemental you control.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // * Target player draws two cards.
        this.getSpellAbility().addMode(new Mode(new DrawCardTargetEffect(2)).addTarget(new TargetPlayer()));

        // * Ashling's Command deals 2 damage to each creature target player controls.
        this.getSpellAbility().addMode(new Mode(new DamageAllControlledTargetEffect(2)).addTarget(new TargetPlayer()));

        // * Target player creates two Treasure tokens.
        this.getSpellAbility().addMode(new Mode(new CreateTokenTargetEffect(new TreasureToken(), 2)).addTarget(new TargetPlayer()));
    }

    private AshlingsCommand(final AshlingsCommand card) {
        super(card);
    }

    @Override
    public AshlingsCommand copy() {
        return new AshlingsCommand(this);
    }
}
