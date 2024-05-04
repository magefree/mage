package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VaultPlunderer extends CardImpl {

    public VaultPlunderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Vault Plunderer enters the battlefield, target player draws a card and loses 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardTargetEffect(1));
        ability.addEffect(new LoseLifeTargetEffect(1).setText("and loses 1 life"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private VaultPlunderer(final VaultPlunderer card) {
        super(card);
    }

    @Override
    public VaultPlunderer copy() {
        return new VaultPlunderer(this);
    }
}
