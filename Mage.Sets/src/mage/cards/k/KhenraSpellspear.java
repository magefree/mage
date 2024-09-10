package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KhenraSpellspear extends CardImpl {

    public KhenraSpellspear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.g.GitaxianSpellstalker.class;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // {3}{U/P}: Transform Khenra Spellspear. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{3}{U/P}")));
    }

    private KhenraSpellspear(final KhenraSpellspear card) {
        super(card);
    }

    @Override
    public KhenraSpellspear copy() {
        return new KhenraSpellspear(this);
    }
}
