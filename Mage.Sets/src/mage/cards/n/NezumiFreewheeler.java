package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NezumiFreewheeler extends CardImpl {

    public NezumiFreewheeler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.h.HideousFleshwheeler.class;

        // Menace
        this.addAbility(new MenaceAbility());

        // When Nezumi Freewheeler enters the battlefield, each player mills three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsEachPlayerEffect(3, TargetController.EACH_PLAYER)));

        // {5}{W/P}: Transform Nezumi Freewheeler. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{W/P}")));
    }

    private NezumiFreewheeler(final NezumiFreewheeler card) {
        super(card);
    }

    @Override
    public NezumiFreewheeler copy() {
        return new NezumiFreewheeler(this);
    }
}
