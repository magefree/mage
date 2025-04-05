package mage.cards.d;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class DirgurIslandDragon extends OmenCard {

    public DirgurIslandDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{5}{U}", "Skimming Strike", "{1}{U}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Skimming Strike
        // Tap up to one target creature. Draw a card.
        this.getSpellCard().getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.finalizeOmen();
    }

    private DirgurIslandDragon(final DirgurIslandDragon card) {
        super(card);
    }

    @Override
    public DirgurIslandDragon copy() {
        return new DirgurIslandDragon(this);
    }
}
